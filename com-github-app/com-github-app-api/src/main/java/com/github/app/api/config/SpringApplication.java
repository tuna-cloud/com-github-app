package com.github.app.api.config;

import com.github.app.api.utils.CmdParase;
import com.github.pagehelper.PageInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.vertx.core.json.JsonObject;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.github.app.api")
@EnableTransactionManagement
@MapperScan("com.github.app.api.dao.mapper")
public class SpringApplication {


    @Bean(name = "dataSource")
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        JsonObject config = CmdParase.getInstance().getServerCfg().getJsonObject("datasource");
        try {
            dataSource.setDriverClass(config.getString("driverClassName"));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        dataSource.setJdbcUrl(config.getString("url"));
        dataSource.setUser(config.getString("username"));
        dataSource.setPassword(config.getString("password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        //分页插件
        Properties props = new Properties();
        props.setProperty("rowBoundsWithCount", "true");
        //添加插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(props);
        bean.setPlugins(new Interceptor[]{pageInterceptor});

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
