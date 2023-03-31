import hilog from '@ohos.hilog';
import {
    AccountAuthManager,
    HuaweiIdAuthParamsHelper,
    HuaweiIdAuthParams,
    AccountAuthResult,
    AuthAccount
} from "@hms-core/account";

const ERROR_SIGNIN_IS_NOT_LOGIN: number = 2002 // 应用尚未授权，需要授权。

export class HuaweiAccountSDKProxy {
    async silentSignIn(option: HuaweiIdAuthParams): Promise<AccountAuthResult<AuthAccount>> {
        let result: AccountAuthResult<AuthAccount>
        await AccountAuthManager.silentSignIn(option).then((message) => {
            // 登录成功
            hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'silentSignIn result ' + result)
            result = message
        }).catch((error) => {
            // 登录失败
            hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'silentSignIn error ' + error)
            result = error
        })

        hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'silentSignIn result ' + result)
        return result
    }

    async signIn(option: HuaweiIdAuthParams): Promise<AccountAuthResult<AuthAccount>> {
        let result: AccountAuthResult<AuthAccount>
        await AccountAuthManager.signIn(option).then((message) => {
            // 登录成功
            hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'signIn result ' + result)
            result = message
        }).catch((error) => {
            // 登录失败
            hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'signIn error ' + error)
            result = error
        })

        hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'signIn result ' + result)
        return result
    }

    async authCodeLogin(): Promise<any> {
        let option = new HuaweiIdAuthParamsHelper()
            .setAuthorizationCode()
            .setId()
            .setProfile()
            .build()

        /**
         * 获取 authCode：
         * 先静默登录，如果静默登录成功，则从返回的AccountAuthResult.data中获取信息，授权登录流程结束；
         * 如果静默登录失败，则调用前台登录授权signIn接口，进行登录授权；
         * 如果前台登录授权接口执行成功，则从返回的AccountAuthResult.data中获取信息，授权登录流程结束；
         * 如果前台登录授权接口执行失败，则从返回的AccountAuthResult.error查看错误原因。
         */
        let result = await this.silentSignIn(option)
        if (result.errCode == ERROR_SIGNIN_IS_NOT_LOGIN) {
            result = await this.signIn(option)
        }
        if (result.errCode != 0) {
            return result
        }

        // TODO 获取到Authorization Code信息后，应用需要发送给应用服务器获取信息

        return result.data
    }

    async idTokenLogin(): Promise<any> {
        let option = new HuaweiIdAuthParamsHelper()
            .setIdToken()
            .setId()
            .setProfile()
            .build()

        /**
         * 获取 idToken：
         * 先静默登录，如果静默登录成功，则从返回的AccountAuthResult.data中获取信息，授权登录流程结束；
         * 如果静默登录失败，则调用前台登录授权signIn接口，进行登录授权；
         * 如果前台登录授权接口执行成功，则从返回的AccountAuthResult.data中获取信息，授权登录流程结束；
         * 如果前台登录授权接口执行失败，则从返回的AccountAuthResult.error查看错误原因。
         */
        let result = await this.silentSignIn(option)
        if (result.errCode == ERROR_SIGNIN_IS_NOT_LOGIN) {
            result = await this.signIn(option)
        }

        if (result.errCode != 0) {
            return result
        }

        // TODO 调用应用服务器验证 ID Token 的有效性

        return result.data
    }

}