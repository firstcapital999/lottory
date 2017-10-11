package com.thinkive;

import com.thinkive.common.authority.util.Appctx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LotteryApplication {

	public static void main(String[] args) {
		Appctx.ctx = SpringApplication.run(LotteryApplication.class, args);

	}
}
