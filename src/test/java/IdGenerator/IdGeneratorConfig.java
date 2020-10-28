package IdGenerator;

/**
 * ID生成器的配置接口
 * @author Ivan.Ma
 */
public interface IdGeneratorConfig {

    /**
     * 获取外协、外购
     * @return
     */
    String getSGWX();

    /**
     * 获取智能生成，智能零单，科技零单，科技生产
     * @return
     */
    String getABK();
    /**
     * 获取分隔符
     * @return
     */
    String getSplitString();

    /**
     * 获取机械生产，试制/研发，电气生产，机械现场，电气现场
     * @return
     */
    String get08467();

    /**
     * 获取初始值/序号
     * @return
     */
    int getInitial();
    /**
     * 获取芜湖工程/空白
     * @return
     */
    String getH();
    /**
     * 获取ID前缀
     * @return
     */
    String getPrefix();

    /**
     * 获取滚动间隔, 单位: 秒
     * @return
     */
    int getRollingInterval();



}