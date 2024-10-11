package hello.servlet.web.frontcontroller.v5;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.V4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.V4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.V4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet{

    // private Map<String, String> handlerMappingMap = new HashMap<>(); // V4만 지원하는 맵이다. V4는 오브젝트를 넣으니 다 지원하기 위해서는 아래처럼 맵을 정의
    private final Map<String, Object> handlerMappingMap = new HashMap<>(); 
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();   

    public FrontControllerServletV5(){
        initHandlerMapping();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMapping() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service()");

        // 핸들러(컨트롤러) 찾아와!
        Object handler = getHandler(req);
        if(handler == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        // 어뎁터 찾아와!
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(req, resp, handler);
        
        // V3 uses paramMap for inner logic. 
        //gets paramMap from HttpServletRequest(done by createParamMap method)
        // Map<String, String> paramMap = createParamMap(req); 

        // we already have controller which is resolved by URI. 
        // now we give controller the paramMap for further process.
        // controllers of V3 returns ModelView(which is consisted by viewName, paramMap)
        // ModelView mv = controller.process(paramMap); 

        // view name is already resolved since we got mv from the controller
        String viewName = mv.getViewName();
        
        // view name here is mere logical name(like save, new-form) so we have to resolve it and generate 'real' view object.
        MyView view = viewResolver(viewName);

        // render means following steps.
        // render(model, req, resp)
        //
        // modelToRequestAttribute(model, req);
        // RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        // dispatcher.forward(req, resp);
        view.render(mv.getModel(), req, resp);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for(MyHandlerAdapter adapter: handlerAdapters){
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("Couldn't find handler adapter for " + handler);
    }

    private Object getHandler(HttpServletRequest req) {
        String requestURI = req.getRequestURI(); 
        return handlerMappingMap.get(requestURI);
    }
    
}
