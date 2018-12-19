package com.xindq.yilan.view.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Config {
    private Condition condition;
    private Action action;
    Map<String, String> datas=new HashMap<>();

    public Config(Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
    }

    /**
     * 根据实时数据启动组态动画
     * @param datas
     */
    public void startUp(Map<String, String> datas){
        this.datas=datas;
        boolean result=true;
        if (condition != null) {
            result = condition.result(datas);
        }
        action.excute(datas,result);
    }

    /**
     * 获取本组态所有需要的测点名
     * @return
     */
    public Set<String> getRequestItems(){
        Set<String> set=new HashSet<>();
        if (condition != null) {
            set.addAll(condition.getItems());
        }
        String item = action.getItem();
        if (item != null) {
            set.add(item);
        }
        return set;
    }

    /**
     * 获取Condition绑定的测点
     * @return
     */
    public Set<String> getConditionItems(){
        if (condition != null) {
            return new HashSet<>(condition.getItems());
        }
        return new HashSet<>();
    }

    /**
     * 获取Action绑定的测点
     * @return
     */
    public String getActionItem(){
        if (action != null) {
            return action.getItem();
        }
        return null;
    }

    /**
     * 获取测点的值
     * @param item
     * @return
     */
    public String getItemValue(String item){
        return datas.get(item);
    }

    public Condition getCondition() {
        return condition;
    }

    public Action getAction() {
        return action;
    }
}
