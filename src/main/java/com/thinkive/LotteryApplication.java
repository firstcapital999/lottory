package com.thinkive;

import com.didispace.swagger.EnableSwagger2Doc;
import com.thinkive.common.authority.util.Appctx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2Doc
public class LotteryApplication {

	public static void main(String[] args) {
		Appctx.ctx = SpringApplication.run(LotteryApplication.class, args);

	}
}
