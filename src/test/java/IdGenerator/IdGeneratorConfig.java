package IdGenerator;

/**
 * ID�����������ýӿ�
 * @author Ivan.Ma
 */
public interface IdGeneratorConfig {

    /**
     * ��ȡ��Э���⹺
     * @return
     */
    String getSGWX();

    /**
     * ��ȡ�������ɣ������㵥���Ƽ��㵥���Ƽ�����
     * @return
     */
    String getABK();
    /**
     * ��ȡ�ָ���
     * @return
     */
    String getSplitString();

    /**
     * ��ȡ��е����������/�з���������������е�ֳ��������ֳ�
     * @return
     */
    String get08467();

    /**
     * ��ȡ��ʼֵ/���
     * @return
     */
    int getInitial();
    /**
     * ��ȡ�ߺ�����/�հ�
     * @return
     */
    String getH();
    /**
     * ��ȡIDǰ׺
     * @return
     */
    String getPrefix();

    /**
     * ��ȡ�������, ��λ: ��
     * @return
     */
    int getRollingInterval();



}