package lfie.danbro.community.community.cache;

import lfie.danbro.community.community.dto.TagDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 获得所有的标签
 */
public class TagCache {
    public static List<TagDto> getTagList(){
        List<TagDto> tagList = new ArrayList<>();

        TagDto devLanguage = new TagDto();
        devLanguage.setCategoryEnName("devLanguage");
        devLanguage.setCategoryName("开发语言");
        devLanguage.setTags(Arrays.asList("javascript","php","css","html","html5","java","node.js","python","c++","c","golang","objective-c","typescript","shell","c#","swift","sass","bash","ruby","less","asp.net","lua","scala","coffeescript","actionscript","rust","erlang","perl"));
        tagList.add(devLanguage);

        TagDto devFramework = new TagDto();
        devFramework.setCategoryEnName("devFramework");
        devFramework.setCategoryName("平台框架");
        devFramework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask ", "ruby-on-rails","tornado", "koa ", "struts"));
        tagList.add(devFramework);

        TagDto server = new TagDto();
        server.setCategoryEnName("server");
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx ", "docker", "apache",   "ubuntu", "centos", "缓存", "tomcat ", "负载均衡",  "unix ", "hadoop ", "windows-server"));
        tagList.add(server);

        TagDto dbAndCache = new TagDto();
        dbAndCache.setCategoryEnName("dbAndCache");
        dbAndCache.setCategoryName("数据库和缓存");
        dbAndCache.setTags(Arrays.asList( "mysql", "redis", "mongodb", "sql", "oracle", "nosql","memcached", "sqlserver", "postgresql", "sqlite"));
        tagList.add(dbAndCache);

        TagDto devTools = new TagDto();
        devTools.setCategoryEnName("devTools");
        devTools.setCategoryName("开发工具");
        devTools.setTags(Arrays.asList("git" ,"github" ,"visual-studio-code" ,"vim" ,"sublime-text" ,"xcode" ,"intellij-idea" ,"eclipse" ,"maven" ,"ide" ,"svn" ,"visual-studio" ,"atom" ,"emacs" ,"textmate" ,"hg"));
        tagList.add(devTools);

        TagDto sysDevice = new TagDto();
        sysDevice.setCategoryEnName("sysDevice");
        sysDevice.setCategoryName("系统设备");
        sysDevice.setTags(Arrays.asList("android " ,"ios " ,"chrome " ,"windows " ,"iphone " ,"firefox " ,"internet-explorer " ,"safari " ,"ipad " ,"opera " ,"apple-watch"));
        tagList.add(sysDevice);

        TagDto others = new TagDto();
        others.setCategoryEnName("others");
        others.setCategoryName("其他");
        others.setTags(Arrays.asList("html5","react.js","搜索引擎","virtualenv","lucene"));
        tagList.add(others);
        return tagList;
    }

    public static String filterTag(String tags){
        String[] split = tags.split(",");
        List<TagDto> tagDtos = getTagList();
        //获得tagDtos里所有的tag
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

}





