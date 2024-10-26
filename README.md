功能描述：  
    用户管理：实现用户的注册、登录、信息修改与删除等功能，确保用户信息的准确性和安全性。  
    角色管理：设定不同的角色（如管理员、普通用户等），并为每个角色分配相应的权限，实现权限的精细化管  
    理。  
    分类管理：对影视资源进行详细的分类管理，方便用户快速查找和筛选感兴趣的影视内容。  
    日志管理：记录用户的登录信息和操作信息，便于追踪和审计系统的使用情况，确保系统的安全性和可追溯  
    性。  
    影视管理：支持影视资源的上传、编辑、删除、播放等功能，为用户提供丰富的影视资源。  
    菜单管理：根据用户的角色和权限，动态生成相应的菜单，确保用户只能访问其有权限的页面和功能。  
设计方案：   
        采用前后端分离的设计模式，前端使用 Vue 框架和 Element-ui 组件库，结合 Vue-admin-template 模板，  
    构建简洁、美观的用户界面。后端采用 Spring Boot 框架和 Mybatis-plus 持久层框架，实现高效、稳定的服务  
    端逻辑。  
        同时，整合 Spring Security 安全框架，确保系统的安全性；使用 Redis 缓存技术，提升系统的响应速度和  
    性能；引入 Knife4j（Swagger2）文档工具，提供 API 接口文档，方便前后端开发和调试。  
        为了处理多个视频同时上传的问题，我们采用了线程池的思想，通过合理分配系统资源，确保视频上传的流畅  
    性和高效性。  
        此外，我们还使用了 Spring AOP 面向切面编程的思想，通过注释的方式实现了操作日志的功能，方便对系  
    统操作进行记录和追踪。  
        为了提供更丰富的影视资源，我们使用了阿里云 OSS 对象存储服务和阿里云视频点播（VOD）服务，实现  
    了影视资源的存储、转码和播放等功能  
