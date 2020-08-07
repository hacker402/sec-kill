package com.seckill.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qingweiqu
 */
public class Generator {

    private static final String BASE_CONTROLLER = "com.laibo.common.BaseController";

    private static final String DATABASE_URL = "localhost:3306";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "Qwer1234";
    private static final String DATABASE = "sec_kill";

    private static final String OUT_FILE_PATH = "/Users/qingweiqu/Desktop/seckill";
    //private static final String OUT_FILE_PATH = "/Users/qingweiqu/Desktop";

    private static final String PACKAGE_PATH = "com.baomidou.ant";
    private static final String MODULE_NAME = "seckill";


    public static void main(String[] args) {

        String[] tableNames = new String[]{"t_user"};

        String[] tablePre = new String[]{"t_"};
        String[] fieldPre = new String[]{"f_"};


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        mpg.setGlobalConfig(initGlobalConfig())
                .setDataSource(initDataSourceConfig())
                .setPackageInfo(initPackageConfig())
                .setCfg(initInjectionConfig())
                .setStrategy(initStrategyConfig(tablePre, fieldPre, tableNames))
                .execute();
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig initInjectionConfig() {

        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return OUT_FILE_PATH + "/src/main/resources/mapper/" + MODULE_NAME
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        return new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        }.setFileOutConfigList(focList);
    }

    /**
     * 数据库表配置
     */
    private static StrategyConfig initStrategyConfig(String[] tablePre, String[] filedPre, String[] tableNames) {
        return new StrategyConfig()
                .setSkipView(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(tablePre)
                .setFieldPrefix(filedPre)
                .setControllerMappingHyphenStyle(true)
                .setChainModel(true)
                .setEntityBooleanColumnRemoveIsPrefix(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setEntityLombokModel(true)
                .setSuperControllerClass(BASE_CONTROLLER)
                .setInclude(tableNames)
                .setRestControllerStyle(true);
    }

    /**
     * 包配置
     */
    private static PackageConfig initPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(MODULE_NAME);
        pc.setParent(PACKAGE_PATH);
        return pc;
    }

    /**
     * 全局配置
     */
    private static GlobalConfig initGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(OUT_FILE_PATH + "/src/main/java");
        gc.setAuthor("qingweiqu");
        gc.setOpen(false)
                .setIdType(IdType.ASSIGN_ID);
        //实体属性 Swagger2 注解
        //gc.setSwagger2(true);
        return gc;
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig initDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.BOOLEAN;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().contains("bit")) {
                            return DbColumnType.BOOLEAN;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().contains("datetime")) {
                            return DbColumnType.LOCAL_DATE_TIME;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        if (fieldType.toLowerCase().contains("timestamp")) {
                            return DbColumnType.LOCAL_DATE_TIME;
                        }
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                })
                .setUrl(String.format("jdbc:mysql://%s/%s?useUnicode=true&useSSL=false&characterEncoding=utf8", DATABASE_URL, DATABASE))
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername(DATABASE_USER)
                .setPassword(DATABASE_PASS)
        ;
        return dsc;
    }

}