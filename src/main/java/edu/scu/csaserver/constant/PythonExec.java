package edu.scu.csaserver.constant;

/**
 * @author Lifeng
 * @date 2022/5/31 16:20
 */
public enum PythonExec {

    /**
     * 计算图分布信息的python配置信息,参数:文件名 分布名
     */
    DIS("/home/lifeng/Develop/backend/csa/pyfile/distribution.py %s %s"),
    /**
     * 链路隐藏和预测,参数:(mask or predict) 文件名 遮盖比例 方法名
     */
    PREDICT("/home/lifeng/Develop/backend/csa/pyfile/mask_predict.py %s %s %s %s");

    /**
     * python命令行执行模板
     */
    private final String EXEC;

    PythonExec(String EXEC) {
        this.EXEC = EXEC;
    }

    public String getEXEC() {
        return EXEC;
    }
}
