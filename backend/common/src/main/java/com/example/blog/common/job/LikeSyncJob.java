package com.example.blog.common.job;

import com.example.blog.common.service.inter.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class LikeSyncJob extends QuartzJobBean {

    @Resource
    private BlogService blogService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("LikeSyncJob Start On {}", sdf.format(new Date()));
        blogService.saveLikeDataFromRedis();
    }
}
