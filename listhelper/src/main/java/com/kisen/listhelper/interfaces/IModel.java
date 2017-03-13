package com.kisen.listhelper.interfaces;

import java.io.Serializable;

/**
 * 标志接口，所有的数据模型必须继承
 * <p>
 *     使用第三方解析工具后，被解析的数据类不能被混淆
 *     在混淆代码时，数据类只需要混淆IModel子类就可以了
 * </p>
 */
public interface IModel extends Serializable {
}
