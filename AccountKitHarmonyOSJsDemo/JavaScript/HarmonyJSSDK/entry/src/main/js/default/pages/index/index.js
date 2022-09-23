import{HuaweiIdAuthParamsHelper, HuaweiIdAuthManager}from'@hmscore/hms-jsb-account'
export default {
    data: {
        title: "",
        signInResult: ""
    },
    onInit() {
        console.log("****onInit****");
        this.title = this.$t('strings.world');
    },
    signIn() {
        this.signInResult = "getSignInIntent start~~~~~~~~~";
        console.log("****test kit_sdk_version is 60100300****");
        console.log("****getSignInIntent****");
        var signInOption = new HuaweiIdAuthParamsHelper().setAuthorizationCode().setAccessToken().setIdToken().setId().setProfile().build();
        console.log("****setAuthorizationCode****");
        //HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，调用huaweiIdAuth.getSignInIntent方法
        HuaweiIdAuthManager.getAuthApi().getSignInIntent(signInOption).then((result)=>{
            //登录成功，获取用户的华为帐号信息
            console.info("****getSignInIntent success****");
            console.info(JSON.stringify(result));
            console.info("昵称:" + result.getDisplayName());
            console.info("头像url:" + result.getAvatarUri());
            console.info("AccessToken:" + result.getAccessToken());
            console.info("setIdToken:" + result.getIdToken());
            this.signInResult += "getSignInIntent success: " + JSON.stringify(result) + "~~~~~~~~~";
        }).catch((error)=>{
            //登录失败
            console.error("****getSignInIntent fail****");
            console.error(JSON.stringify(error));
            this.signInResult += "getSignInIntent fail: " + JSON.stringify(error) + "~~~~~~~~~";
        });
    },
    silentSignIn() {
        this.signInResult = "silentSignIn start~~~~~~~~~";
        console.log("****silentSignIn****");
        var signInOption = new HuaweiIdAuthParamsHelper().setAuthorizationCode().setAccessToken().setIdToken().setId().setProfile().build();
        console.log("****setAuthorizationCode****");
        //HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，调用huaweiIdAuth.getSignInIntent方法
        HuaweiIdAuthManager.getAuthApi().silentSignIn(signInOption).then((result)=>{
            //登录成功，获取用户的华为帐号信息
            console.info("****silentSignIn success****");
            console.info(JSON.stringify(result));
            console.info("昵称:" + result.getDisplayName());
            console.info("头像url:" + result.getAvatarUri());
            console.info("AccessToken:" + result.getAccessToken());
            console.info("setIdToken:" + result.getIdToken());
            this.signInResult += "silentSignIn success: " + JSON.stringify(result) + "~~~~~~~~~";
        }).catch((error)=>{
            //登录失败
            console.error("****silentSignIn fail****");
            console.error(JSON.stringify(error));
            this.signInResult += "silentSignIn fail: " + JSON.stringify(error) + "~~~~~~~~~";
        });
    },
    cancelAuthorization() {
        this.signInResult = "cancelAuthorization start~~~~~~~~~";
        console.log("****cancelAuthorization****");
        //HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，调用huaweiIdAuth.getSignInIntent方法
        HuaweiIdAuthManager.getAuthApi().cancelAuthorization().then((result)=>{
            //登录成功，获取用户的华为帐号信息
            console.info("****cancelAuthorization success****");
            this.signInResult += "cancelAuthorization success: " + JSON.stringify(result) + "~~~~~~~~~";
        }).catch((error)=>{
            //登录失败
            console.error("****cancelAuthorization fail****");
            console.error(JSON.stringify(error));
            this.signInResult += "cancelAuthorization fail: " + JSON.stringify(error) + "~~~~~~~~~";
        });
    },
    signOut() {
        this.signInResult = "signOut start~~~~~~~~~";
        console.log("****signOut****");
        //HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，调用huaweiIdAuth.getSignInIntent方法
        HuaweiIdAuthManager.getAuthApi().signOut().then((result)=>{
            //登录成功，获取用户的华为帐号信息
            console.info("****signOut success****");
            this.signInResult += "signOut success: " + JSON.stringify(result) + "~~~~~~~~~";
        }).catch((error)=>{
            //登录失败
            console.error("****signOut fail****");
            console.error(JSON.stringify(error));
            this.signInResult += "signOut fail: " + JSON.stringify(error) + "~~~~~~~~~";
        });
    }
}
