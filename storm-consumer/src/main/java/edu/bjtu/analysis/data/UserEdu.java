package edu.bjtu.analysis.data;

import static edu.bjtu.analysis.StormConsumer.split;

/**
 * @author huangweidong
 * @date 2018/06/18
 *
 * 用户id	学历	学校	专业
 * Uid	Degree	SchoolName	MajorStr
 * U0000001	4	北大资源学院	NULL
 * U0000002	3	北京航空航天大学	计算机软件
 * U0000003	6	西南交大	网络工程
 *
 *
 * 说明：
 *   1, Degree字段值代表的意思：
 *      -15：为初中
 *      -10:高中
 *      -5:中技
 *      1:中专
 *      2:大专
 *      3:本科
 *      4:硕士
 *      5:MBA
 *      6:博士
 *      7:博士后
 */
public class UserEdu {
    private String uid;
    private Integer degree;
    private String schoolName;
    private String major;

    public UserEdu(String line) {
        String[] values = line.split(String.valueOf(split));
        if (values.length != 4) {
            throw new IllegalArgumentException("参数不合法：" + line);
        }
        this.uid = values[0];
        this.degree = Integer.valueOf(values[1]);
        this.schoolName = values[2];
        this.major = values[3];
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
