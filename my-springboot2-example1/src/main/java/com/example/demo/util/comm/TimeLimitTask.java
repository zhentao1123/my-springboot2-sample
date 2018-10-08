package com.example.demo.util.comm;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeLimitTask<RESULT> {

	Logger logger = LoggerFactory.getLogger(TimeLimitTask.class);
	
	private ExecutorService singleThreadExecutor = null;
	private ExecutorService multThreadExecutor = null;
	
	//处理map
	private ConcurrentHashMap<String, FutureTask<RESULT>> futureMap;
	
	public TimeLimitTask() {
		super();
		futureMap = new ConcurrentHashMap<String, FutureTask<RESULT>>();
	}
	
	/**
	 * 单线程调用方式
	 * @param timeout
	 * @param call
	 * @return
	 * @throws Exception
	 */
	public RESULT doTask(Long timeout, Callable<RESULT> call) throws Exception{
		if(null == initSingleThreadExecutor()){
			throw new Exception("No ExecutorService");
		}
		try {
            Future<RESULT> future = singleThreadExecutor.submit(call);
            RESULT obj = future.get(timeout, TimeUnit.MILLISECONDS);
            logger.debug("------任务成功");
            return obj;
        } catch (TimeoutException e) {
            logger.debug("------任务处理超时");  
            throw e;
        } catch (Exception e) {
            logger.debug("------任务处理失败");
            throw e;
        }finally{
        	try{
        		singleThreadExecutor.shutdownNow();
        	}catch(Exception e){}
        }
	}
	
	public void addBatchTask(String key, Callable<RESULT> call) throws Exception{
		if(null == initMultThreadExecutor()){
			throw new Exception("No ExecutorService");
		}
		try {
            FutureTask<RESULT> future = new FutureTask<RESULT>(call);
            futureMap.put(key, future);
            //getMultThreadExecutor().execute(future);
            getMultThreadExecutor().submit(future);
            logger.debug("------任务添加成功");
        } catch (Exception e) {
            logger.debug("------任务添加失败");
        }
	}
	
	public Boolean isBatchTaskDone() throws Exception {
		while(true) {
			Integer done = 0;
			for(FutureTask<RESULT> ft : futureMap.values()) {
				if(ft.isDone()) {
					done++;
				}
			}
			if(done==futureMap.size()) {
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw e;
			}
		}
		return true;
	}
	
	public RESULT getBatchResult(String key, Long timeout) throws Exception{
		RESULT result = null;
		if(null == initMultThreadExecutor()){
			throw new Exception("No ExecutorService");
		}
		FutureTask<RESULT> future = futureMap.get(key);
		if(null==future){
			return null;
		}
		try {
			result = future.get(timeout, TimeUnit.MILLISECONDS);
            logger.debug("------获取任务结果成功");
        } catch (Exception e) {
        		future.cancel(true);
            logger.debug("------获取任务结果失败");
        }
		return result;
	}
	
	/**
	 * 关闭多线程执行器
	 * 执行批量处理后必须调用
	 */
	public void shutdownMultThreadExecutor(){
		try{
			if(null!=multThreadExecutor){
				multThreadExecutor.shutdownNow();
			}
		}catch (Exception e) {}
	}
	
	public void setCachedThreadPool(ExecutorService cachedThreadPool) {
		this.multThreadExecutor = cachedThreadPool;
	}

	public ExecutorService getMultThreadExecutor() {
		return multThreadExecutor;
	}

	private ExecutorService initSingleThreadExecutor(){
		if(null == singleThreadExecutor){
			singleThreadExecutor = Executors.newSingleThreadExecutor();
		}
		return singleThreadExecutor;
	}
	
	private ExecutorService initMultThreadExecutor(){
		if(null == multThreadExecutor){
			multThreadExecutor = Executors.newCachedThreadPool();
		}
		return multThreadExecutor;
	}
}
