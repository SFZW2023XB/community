package life.fuzhong.community.cache;

import life.fuzhong.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public static List<TagDTO> get() {
        List<TagDTO> tagDTOS = new ArrayList<>();

        // 开发语言
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Java", "Python", "C++", "C", "C#", "Go", "Rust", "JavaScript", "TypeScript", "PHP", "Swift", "Kotlin", "Dart", "Ruby", "Perl"));
        tagDTOS.add(program);

        // 开发框架
        TagDTO framework = new TagDTO();
        framework.setCategoryName("开发框架");
        framework.setTags(Arrays.asList("Spring Boot", "Spring", "Django", "Flask", "FastAPI", "Express", "Vue.js", "React", "Angular", "Svelte", "Next.js", "Nuxt.js", "Laravel", "Ruby on Rails"));
        tagDTOS.add(framework);

        // 数据库
        TagDTO database = new TagDTO();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("MySQL", "PostgreSQL", "MongoDB", "Redis", "SQLite", "MariaDB", "Oracle", "SQL Server", "Cassandra", "Elasticsearch", "Firebase"));
        tagDTOS.add(database);

        // 云计算 & DevOps
        TagDTO devOps = new TagDTO();
        devOps.setCategoryName("云计算 & DevOps");
        devOps.setTags(Arrays.asList("Docker", "Kubernetes", "AWS", "Azure", "Google Cloud", "Jenkins", "GitHub Actions", "Terraform", "Ansible", "CI/CD", "Serverless"));
        tagDTOS.add(devOps);

        // 操作系统
        TagDTO os = new TagDTO();
        os.setCategoryName("操作系统");
        os.setTags(Arrays.asList("Linux", "Windows", "macOS", "Ubuntu", "CentOS", "Debian", "Fedora", "Arch Linux"));
        tagDTOS.add(os);

        // 人工智能 & 机器学习
        TagDTO ai = new TagDTO();
        ai.setCategoryName("人工智能 & 机器学习");
        ai.setTags(Arrays.asList("深度学习", "机器学习", "计算机视觉", "自然语言处理", "PyTorch", "TensorFlow", "Keras", "Scikit-learn", "OpenCV", "LLM"));
        tagDTOS.add(ai);

        // 网络安全
        TagDTO security = new TagDTO();
        security.setCategoryName("网络安全");
        security.setTags(Arrays.asList("加密", "安全协议", "漏洞利用", "渗透测试", "CTF", "密码学", "防火墙", "TLS/SSL", "SQL注入", "XSS"));
        tagDTOS.add(security);

        // 后端技术
        TagDTO backend = new TagDTO();
        backend.setCategoryName("后端技术");
        backend.setTags(Arrays.asList("微服务", "API 设计", "GraphQL", "RESTful API", "gRPC", "消息队列", "RabbitMQ", "Kafka", "Spring Cloud", "Quarkus"));
        tagDTOS.add(backend);

        // 前端技术
        TagDTO frontend = new TagDTO();
        frontend.setCategoryName("前端技术");
        frontend.setTags(Arrays.asList("HTML", "CSS", "SCSS", "JavaScript", "TypeScript", "WebAssembly", "Tailwind CSS", "Bootstrap", "Vue.js", "React", "Three.js"));
        tagDTOS.add(frontend);

        return tagDTOS;
    }
}

