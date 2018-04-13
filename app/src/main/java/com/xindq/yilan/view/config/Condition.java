package com.xindq.yilan.view.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Condition {
    /**
     * 字符串表示的条件：sql语句where之后形式：{}为占位符
     * 例如：{item[0]} = 5 and ({item[1]} < 10 or {item[2]} > 9)
     */
    private String condition;
    private List<String> items = new ArrayList<>();

    public Condition(String condition) {
        condition = condition.replace(" ", "");
        condition = condition.replace("and", "&");
        condition = condition.replace("or", "|");
        StringBuilder sb = new StringBuilder(condition);
        while (sb.indexOf("{") != -1) {
            int start = sb.indexOf("{");
            int end = sb.indexOf("}");
            String item = sb.substring(start + 1, end);
            items.add(item);
            sb.delete(start, end + 1);
        }
        this.condition = condition;
    }

    /**
     * 获取本条件对象是否满足。
     * @param datas
     * @return
     */
    public boolean result(Map<String, String> datas){
        if (condition==null||"".equals(condition.trim())) return true;
        String fullCondition = getFullCondition(datas);
        fullCondition=infixToSuffix(fullCondition);
        return validateSuffix(fullCondition);
    }

    /**
     * 判断由多个形如 3>2 形式的表达式通过 | 或 & 链接的后缀表达式是否成立。
     * @param condition
     * @return
     */
    private boolean validateSuffix(String condition){
        String[] split = condition.split(" ");
        Stack<Boolean> stack=new Stack<>();
        for (String s : split) {
            if ("".equals(s)) continue;
            if ("&".equals(s)){
                Boolean p1 = stack.pop();
                Boolean p2 = stack.pop();
                stack.push(p1&&p2);
            }else if ("|".equals(s)){
                Boolean p1 = stack.pop();
                Boolean p2 = stack.pop();
                stack.push(p1||p2);
            }else {
                stack.push(validateOneSection(s));
            }
        }
        return stack.peek();
    }

    /**
     * 判断单个形如 3>2 形式的表达式是否成立。
     * @param s
     * @return
     */
    private boolean validateOneSection(String s) {
        try {
            if (s.contains("<=")){
                String[] split = s.split("<=");
                double d0 = Double.parseDouble(split[0]);
                double d1 = Double.parseDouble(split[1]);
                return d0<=d1;
            }else if (s.contains(">=")){
                String[] split = s.split(">=");
                double d0 = Double.parseDouble(split[0]);
                double d1 = Double.parseDouble(split[1]);
                return d0>=d1;
            }else if (s.contains("<")){
                String[] split = s.split("<");
                double d0 = Double.parseDouble(split[0]);
                double d1 = Double.parseDouble(split[1]);
                return d0<d1;
            }else if (s.contains(">")){
                String[] split = s.split(">");
                double d0 = Double.parseDouble(split[0]);
                double d1 = Double.parseDouble(split[1]);
                return d0>d1;
            }else if (s.contains("=")){
                String[] split = s.split("=");
                if (split[0].equals(split[1])) return true;
                else {
                    double d0 = Double.parseDouble(split[0]);
                    double d1 = Double.parseDouble(split[1]);
                    return d0==d1;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 前缀表达式转为中缀表达式，每个元素用空格分格。（运算符为|或&）
     * @param condition
     * @return
     */
    private String infixToSuffix(String condition) {
        List<String> list = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (c!='('&&c!=')'&&c!='&'&&c!='|'){
                sb.append(c);
            }else {
                sb.append(" ");
                if (c == '(' || c == '&') stack.push(c);
                else if (c == '|') {
                    while (!stack.empty()&&(stack.peek() == '&'||stack.peek() == '|')) {
                        sb.append(" ");
                        sb.append(stack.pop());
                    }
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.empty()&&stack.peek() != '(') {
                        sb.append(" ");
                        sb.append(stack.pop());
                    }
                    stack.pop();
                }
            }
        }
        while (!stack.empty()){
            sb.append(" ");
            sb.append(stack.pop());
        }

        return sb.toString();
    }


    /**
     * 获取通过传入的实时数据填充占位符得到的表达式。
     * @param datas
     * @return
     */
    private String getFullCondition(Map<String, String> datas) {
        String newCondition = new String(condition);
        for (String item : items) {
            String key = "{" + item + "}";
            String value = datas.get(item);
            newCondition = newCondition.replace(key, value);
        }
        return newCondition;
    }

    public List<String> getItems() {
        return items;
    }

    public String getCondition() {
        return condition;
    }
}
