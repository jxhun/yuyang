package cn.com.yuyang.pojo;


public class Haoyoubiao {

    private long id;
    private long benRenId;
    private long haoYouId;
    private Denglu dengLu;  // 好友mapper.xml 中用到
    private Renyuandangan renyuandangan;

    public Renyuandangan getRenyuandangan() {
        return renyuandangan;
    }

    public void setRenyuandangan(Renyuandangan renyuandangan) {
        this.renyuandangan = renyuandangan;
    }

    public Denglu getDengLu() {
        return dengLu;
    }

    public void setDengLu(Denglu dengLu) {
        this.dengLu = dengLu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getBenRenId() {
        return benRenId;
    }

    public void setBenRenId(long benRenId) {
        this.benRenId = benRenId;
    }


    public long getHaoYouId() {
        return haoYouId;
    }

    public void setHaoYouId(long haoYouId) {
        this.haoYouId = haoYouId;
    }

}
