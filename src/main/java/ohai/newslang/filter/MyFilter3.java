package ohai.newslang.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getMethod().equals("POST")){
            String headerAuth = req.getHeader("Authorization");
            if (headerAuth.equals("cos")){
                System.out.println(headerAuth);
                System.out.println("필터3");
                chain.doFilter(req,res);
            }else{
                PrintWriter out = res.getWriter();
                out.println("no");
            }
        }
    }
}
