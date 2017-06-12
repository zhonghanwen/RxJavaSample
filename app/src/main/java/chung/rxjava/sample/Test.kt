package chung.rxjava.sample

import rx.Observer

/**
 * Created by zhonghanwen on 2017/6/12.
 */

object Test {

    private fun test() {
        val observer = object : Observer<String> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(s: String) {

            }
        }
    }
}
