package com.louisgeek.dropdownviewlib.javabean;

import java.util.List;

/**
 * Created by louisgeek on 2016/8/9.
 */
public class ClassfiyBean {
    private int ID;
    private String beanID;
    private String name;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;
    public List<ChildClassfiyBean> getChildClassfiyBeanList() {
        return childClassfiyBeanList;
    }

    public void setChildClassfiyBeanList(List<ChildClassfiyBean> childClassfiyBeanList) {
        this.childClassfiyBeanList = childClassfiyBeanList;
    }

    private List<ChildClassfiyBean> childClassfiyBeanList;
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBeanID() {
        return beanID;
    }

    public void setBeanID(String beanID) {
        this.beanID = beanID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ChildClassfiyBean{
        private int ID;
        private String beanID;
        private String name;
        private String count;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private boolean selected;
        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getBeanID() {
            return beanID;
        }

        public void setBeanID(String beanID) {
            this.beanID = beanID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
