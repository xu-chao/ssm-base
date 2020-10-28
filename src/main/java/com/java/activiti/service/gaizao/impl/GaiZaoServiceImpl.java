package com.java.activiti.service.gaizao.impl;

import com.java.activiti.dao.gaizao.GaiZaoDao;
import com.java.activiti.model.Project;
import com.java.activiti.model.gaizao.GaiZao;
import com.java.activiti.service.gaizao.GaiZaoService;
import com.java.activiti.util.aop.Operation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("gaiZaoService")
public class GaiZaoServiceImpl implements GaiZaoService {

    @Resource
    private GaiZaoDao gaiZaoDao;

    @Override
    public int gaiZaoCount(List<Project> projects) {
        return gaiZaoDao.gaiZaoCount(projects);
    }

    @Override
    public List<GaiZao> gaiZaoPage(Map<String, Object> map, List<Project> projects) {
        return gaiZaoDao.gaiZaoPage(map, projects);
    }

    @Override
    public List<GaiZao> gaiZaoDetail(Map<String, Object> map) {
        return gaiZaoDao.gaiZaoDetail(map);
    }

    @Override
    public int addGaiZao(GaiZao gaiZao) {
        return gaiZaoDao.addGaiZao(gaiZao);
    }

    @Override
    public int updateGaiZao(GaiZao gaiZao) {
        return gaiZaoDao.updateGaiZao(gaiZao);
    }

    /**
     * 批量删除城市
     *
     * @param id
     * @return
     */
    @Operation(value = "批量删除改造记录")
    public int deleteGaiZaoById(List<String> id) {
        return gaiZaoDao.deleteGaiZao(id);
    }
}
