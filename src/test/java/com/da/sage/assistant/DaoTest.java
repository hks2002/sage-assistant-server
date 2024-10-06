/*********************************************************************************************************************
 * @Author                : Robert Huang<56649783@qq.com>                                                            *
 * @CopyRight             : Dedienne Aerospace China ZhuHai                                                          *
 * @CreatedDate           : 2025-07-14 00:57:26                                                                      *
 * @LastEditDate          : 2025-07-15 13:23:37                                                                      *
 * @LastEditors           : Robert Huang<56649783@qq.com>                                                            *
 ********************************************************************************************************************/

package com.da.sage.assistant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.da.sage.assistant.utils.PackageUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class DaoTest {

  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  public <T> T getMapperInstance(Class<T> mapperInterface) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    MapperProxyFactory<T> proxyFactory = new MapperProxyFactory<>(mapperInterface);
    return proxyFactory.newInstance(sqlSession);
  }

  public void testDao(String className) {
    try {
      log.info("Test Mapper: {}", className);
      Class<?> clazz = Class.forName(className);
      Object instance = getMapperInstance(clazz);

      Method[] methods = clazz.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        Method method = methods[i];
        method.setAccessible(true);
        log.info("Method: {}", method.getName());

        Parameter[] parameters = method.getParameters();
        List<Object> parameterValues = new ArrayList<>();

        for (int j = 0; j < parameters.length; j++) {
          String paramName = parameters[j].getName();

          switch (paramName) {
            case "Site":
              parameterValues.add("ZHU");
              break;
            case "PN":
              parameterValues.add("956A1001G01%");
              break;
            case "Pn":
              parameterValues.add("956A1001_A");
              break;
            case "PnRoot":
              parameterValues.add("956A1001G01");
              break;
            case "DateFrom":
              parameterValues.add("2022-01-01");
              break;
            case "DateTo":
              parameterValues.add("2022-01-07");
              break;
            case "Limit":
              parameterValues.add(10);
              break;
            case "LastN":
              parameterValues.add(1);
              break;
            case "Count":
              parameterValues.add(1);
              break;
            case "Target":
              parameterValues.add("NetPrice");
              break;
            case "Currency":
              parameterValues.add("USD");
              break;
            case "Sour":
              parameterValues.add("RMB");
              break;
            case "Dest":
              parameterValues.add("RMB");
              break;
            case "Date":
              parameterValues.add("2022-01-01");
              break;
            case "Year":
              parameterValues.add("2022");
              break;
            case "AccountNO":
              parameterValues.add("1002210");
              break;
            case "CustomerCode":
              parameterValues.add("00870");
              break;
            case "SupplierCode":
              parameterValues.add("00870");
              break;
            case "InvoiceNO":
              parameterValues.add("ZFC2501001");
            default:
          }
        }

        log.info("{}", method.invoke(instance, parameterValues.toArray()));

      }
    } catch (
        ClassNotFoundException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException
        | SecurityException e) {
      log.error("{}", e);
    }
  }

  @Test
  public void testAllDao() {
    var daoList = PackageUtils.getClassesInJarPackage("com.da.sage.assistant.dao");

    for (String className : daoList) {
      log.info("Mapper found: {}", className);
      testDao(className);
    }
  }

  @Test
  public void testOneDao() {
    testDao("com.da.sage.assistant.dao.PnMapper");
  }

}
