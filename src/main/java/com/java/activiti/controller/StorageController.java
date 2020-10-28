package com.java.activiti.controller;

import com.java.activiti.dao.NeedGoodsDao;
import com.java.activiti.model.Storage;
import com.java.activiti.service.NeedGoodsService;
import com.java.activiti.service.StorageService;
import com.java.activiti.util.ResponseUtil;
import com.java.activiti.util.UuidUtil;
import io.swagger.models.auth.In;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/storage")
public class StorageController {

    @Resource
    private StorageService storageService;
    @Resource
    private NeedGoodsDao needGoodsDao;

    /**
     * ÐÂÔö¿â´æ
     * @return
     * @throws Exception
     */
    @RequestMapping("/addStorage")
    public String addStorage(HttpServletResponse response, Storage storage,
                             @RequestParam(value = "NID") String NID, @RequestParam(value = "goodsId") String goodsId) throws Exception{
        storage.setStorageId("S" + UuidUtil.uuidUtil());
        int storageResult=storageService.addStorage(storage);
        needGoodsDao.updateStorageId(NID,goodsId,storage.getStorageId());
        JSONObject json=new JSONObject();
        if(storageResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * ÐÞ¸Ä¿â´æ
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateStorage")
    public String updateStorage(HttpServletResponse response,Storage storage) throws Exception{
        int storageResult=storageService.updateStorage(storage);
        JSONObject json=new JSONObject();
        if(storageResult>0){
            json.put("success", true);
        }else{
            json.put("success", false);
        }
        ResponseUtil.write(response, json);
        return null;
    }

    /**
     * ÐÞ¸Ä¿â´æ
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateStorageRows")
    public String updateStorageRows(HttpServletResponse response,
                                    @RequestParam(value = "NID") String NID, @RequestParam(value = "goodsId") String goodsId,
                                    @RequestParam(value = "storageId") String storageId, @RequestParam(value = "mount") String mount,
                                    @RequestParam(value = "mountBack") String mountBack,@RequestParam(value = "mountIn") String mountIn) throws Exception{
        int storageResult;
        JSONObject json=new JSONObject();
        Storage storage = new Storage();
        try {
            if(!mount.equals("undefined")){
                storage.setMount(Integer.parseInt(mount));
            }
            if(!mountBack.equals("undefined")){
                storage.setMountBack(Integer.parseInt(mountBack));
            }
            if(!mountIn.equals("undefined")){
                storage.setMountIn(Integer.parseInt(mountIn));
            }
        }catch (Exception e){
            json.put("success", "error");
            ResponseUtil.write(response, json);
            return null;
        }
        if(storageId.equals("undefined") || storageId.equals("")){
            storage.setStorageId("S" + UuidUtil.uuidUtil());
            storageResult=storageService.addStorage(storage);
            needGoodsDao.updateStorageId(NID,goodsId,storage.getStorageId());
        }else {
            storage.setStorageId(storageId);
            storageResult=storageService.updateStorage(storage);
        }
        if(storageResult>0){
            json.put("success", "success");
        }else{
            json.put("success", "fail");
        }
        ResponseUtil.write(response, json);
        return null;
    }
}
