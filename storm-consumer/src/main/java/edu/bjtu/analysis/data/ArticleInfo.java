package edu.bjtu.analysis.data;


/**
 * @author baohehe
 * @date 2018/06/18
 * <p>
 * 文章id	类型	置顶	状态	博文版块
 * Aid	Type	IsTop	Status	domain
 * U0000001	1	0	1	自然语言处理
 * U0000002	2	0	1	python
 * U0000003	4	1	2	HTML
 * <p>
 * <p>
 * 说明：
 * 1, Type字段值代表的意思：
 * 1：原创
 * 2：转载
 * 4：翻译
 * <p>
 * 2, Status字段值代表的意思：
 * 1：正常
 * 2：已删除
 * <p>
 * 3, IsTop字段值代表的意思：
 * 0：不在首页显示
 * 1：在首页显示
 * <p>
 * 4，domain字段代表的意思：
 * 文章所属版块层次关系，详见右图
 */
public class ArticleInfo {
    private String aid;
    private Integer type;
    private Integer status;
    private Integer isTop;
    private String domain;

    public ArticleInfo(String line) {
        String[] values = line.split(String.valueOf(""));
        if (values.length != 5) {
            throw new IllegalArgumentException("参数不合法：" + line);
        }
        this.aid = values[0];
        this.type = Integer.valueOf(values[1]);
        this.status = Integer.valueOf(values[2]);
        this.isTop = Integer.valueOf(values[3]);
        this.domain = values[4];
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
