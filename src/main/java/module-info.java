open module inventory.app {
    requires spring.context;
    requires spring.web;
    requires spring.data.commons;
    requires spring.data.mongodb;
    requires lombok;
    requires org.mongodb.driver.core;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
}