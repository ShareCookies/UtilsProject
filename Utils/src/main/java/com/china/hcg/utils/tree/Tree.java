package com.china.hcg.utils.tree;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Tree<T> implements Serializable {
    /**
     * 节点ID
     */
    protected String id;
    /**
     * 显示节点文本
     */
    protected String text;
    /**
     * 节点状态，open closed
     */
    protected Map<String, Object> state;
    /**
     * 节点是否被选中 true false
     */
    protected boolean checked = false;
    /**
     * 节点属性
     */
    protected Map<String, Object> attributes;

    /**
     * 节点的子节点
     */
    protected List<Tree<T>> children = new ArrayList<Tree<T>>();

    /**
     * 父ID
     */
    protected String parentId;
    /**
     * 是否有父节点
     */
    protected boolean hasParent = false;
    /**
     * 是否有子节点
     */
    protected boolean hasChildren = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean isParent) {
        this.hasParent = isParent;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setChildren(boolean isChildren) {
        this.hasChildren = isChildren;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Tree(String id, String text, List<Tree<T>> children, String parentId) {
        this.id = id;
        this.text = text;
        this.children = children;
        this.parentId = parentId;
    }

    public Tree(String id, String text, Map<String, Object> state, boolean checked, Map<String, Object> attributes,
                List<Tree<T>> children, boolean isParent, boolean isChildren, String parentID) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.checked = checked;
        this.attributes = attributes;
        this.children = children;
        this.hasParent = isParent;
        this.hasChildren = isChildren;
        this.parentId = parentID;
    }

    public Tree() {
        super();
    }

    @Override
    public String toString() {

        return JSON.toJSONString(this);
    }


}