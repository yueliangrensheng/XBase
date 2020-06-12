package com.yazao.lib.xbase;

/**
 * 类描述：
 *
 * @author zhaishaoping
 * @data 10/04/2017 10:49 AM
 */

public interface WBaseView {

    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();
}
