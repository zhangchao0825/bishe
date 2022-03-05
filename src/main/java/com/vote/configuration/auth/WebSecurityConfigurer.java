package com.vote.configuration.auth;

import com.vote.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyOncePerRequestFilter myOncePerRequestFilter;
    @Autowired
    private MyLogoutHandler myLogoutHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private FilesUtil filesUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //第1步：开启跨域自动搜素corsConfigurationSource
        http.cors();


        //第2步：让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();

        //第3步：请求权限配置
        //放行注册API请求，其它任何请求都必须经过身份验证.
        http.authorizeRequests()
                //静态资源
                .and().authorizeRequests().antMatchers(filesUtil.getRelativePath()+"**").permitAll()
                .and().authorizeRequests().antMatchers("/user/photoUpload").permitAll()
                .and().authorizeRequests().antMatchers("/user/register").permitAll()
                .and().authorizeRequests().antMatchers("/user/userExists").permitAll()
                .and().authorizeRequests().antMatchers("/vote/typeList").permitAll()
                .and().authorizeRequests().antMatchers("/vote/getHotVote").permitAll()
                .and().authorizeRequests().antMatchers("/vote/getVote").permitAll()
                .and().authorizeRequests().antMatchers("/vote/getVoteList").permitAll()
//                //获得文章
//                .and().authorizeRequests().antMatchers("/articleManager/getArticleList").permitAll()
//                //获得部门列表
//                .and().authorizeRequests().antMatchers("/login/getPermissionList").permitAll()
//                //上传文件
//                .and().authorizeRequests().antMatchers("/filesManager/upload").permitAll()
//                //初始化账户信息
//                .and().authorizeRequests().antMatchers("/login/initializationAccount").permitAll()
//                //显示文章
//                .and().authorizeRequests().antMatchers("/articleManager/getArticleById").permitAll()
//                //获取文章作者附件
//                .and().authorizeRequests().antMatchers("/articleManager/getAuthFiles").permitAll()
//                //获取文章的作者信息
//                .and().authorizeRequests().antMatchers("/articleManager/getAuthMessage").permitAll()
//                //预览文件
//                .and().authorizeRequests().antMatchers("/filesManager/PreviewFile").permitAll()
//                //删除临时文件
//                .and().authorizeRequests().antMatchers("/filesManager/removeTempFile").permitAll()
//                //下载文件
//                .and().authorizeRequests().antMatchers("/filesManager/downloadFile").permitAll()
//                //搜索
//                .and().authorizeRequests().antMatchers("/articleManager/searchArticleByKeyWord").permitAll()
                .anyRequest().authenticated();

        //第4步：拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);


        //第5步：拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器
        http.addFilterAt(myUsernamePasswordAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class);



        //第6步：处理异常情况：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint).accessDeniedHandler(myAccessDeniedHandler);

        //第7步：登录,因为使用前端发送JSON方式进行登录，所以登录模式不设置也是可以的。
        http.formLogin();

        //第8步：退出
        http.logout().addLogoutHandler(myLogoutHandler).logoutSuccessHandler(myLogoutSuccessHandler);
//        // 表单认证
//        http.formLogin()
//                .loginProcessingUrl("/login") //当发现/login 时认为是登录，需要执行UserDetailsServiceImpl
//                .successForwardUrl("/toSuccess") //此处是 post 请求
//                .failureForwardUrl("/fail");//登录失败跳转地址
//        // url 拦截 http.authorizeRequests() .antMatchers("/login.html") .permitAll()
//        //login.html 不需要被认证 .anyRequest().authenticated();
//        //所有的请求都必须被认证。必须登录后才能访问。
    }

    @Bean
    MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        //成功后处理
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        //失败后处理
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
