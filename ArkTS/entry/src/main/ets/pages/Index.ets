import hilog from '@ohos.hilog';
import { HuaweiAccountSDKProxy } from '../hwid/HuaweiAccountSDKProxy'

const INDENT: number = 4

@Entry
@Component
struct Index {
  @State loginState: boolean = false
  @State nickname: string = ""
  @State avatarUri: string = ""
  @State email: string = ""
  @State extraData: string = ""
  @State showText: string = ""
  @State loginLoading: boolean = false
  accountKit = new HuaweiAccountSDKProxy

  build() {
    Flex() {
      Column({ space: 10 }) {
        Text('HMOS AccountKit Demo')
          .fontSize(20).fontWeight(FontWeight.Normal)
          .align(Alignment.TopStart).margin({ top: 48 })

        if (this.loginLoading) {
          Image($r('app.media.loading'))
            .height(48).width(48)
            .margin({ top: 64 })
        } else {
          Row({ space: 12 }) {
            Button() {
              Image(this.avatarUri ? this.avatarUri : $r("app.media.ic_head_portrait"))
                .width(48)
                .height(48)
                .borderRadius(24)
            }

            Column() {
              Text(this.nickname ? this.nickname : $r('app.string.huawei_id_login'))
                .fontSize(24)
              Text(this.email)
                .fontSize(12).fontColor(Color.Gray)
                .visibility((this.email == "") ? Visibility.Hidden : Visibility.Visible)
            }
          }.margin({ top: 64 })
        }

        Button({ type: ButtonType.Normal }) {
          Row() {
            Image($r('app.media.hw_24x24_logo_white'))
              .height(24).width(24)
            Blank().width(16)
            Text('AuthCode Login')
              .fontSize(24).fontColor(Color.White)
          }.margin({ left: 16, right: 16 })
        }
        .margin({ top: 64 })
        .backgroundColor($r('app.color.hwid_login_button_background'))
        .width('75%')
        .height(40)
        .borderRadius(8)
        .onClick(() => {
          this.loginLoading = true
          this.accountKit.authCodeLogin().then((data) => {
            hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'authCodeLogin data ' + data);
            this.loginLoading = false
            try {
              this.nickname = data.displayName
              this.avatarUri = data.avatarUri
              this.email = data.email
              this.showText = data.serverAuthCode ? ("serverAuthCode: " + data.serverAuthCode)
                                                  : JSON.stringify(data, null, INDENT)
            } catch (e) {
              hilog.warn(0x0000, 'DEMO_TAG', '%{public}s', 'authCodeLogin e ' + e);
              this.showText = JSON.stringify(data, null, INDENT)
            }
          })
        })

        Button({ type: ButtonType.Normal }) {
          Row() {
            Image($r('app.media.hw_24x24_logo_white'))
              .height(24).width(24)
            Blank().width(16)
            Text('IdToken Login')
              .fontSize(24).fontColor(Color.White)
          }.margin({ left: 16, right: 16 })
        }
        .backgroundColor($r('app.color.hwid_login_button_background'))
        .width('75%')
        .height(40)
        .borderRadius(8)
        .onClick(() => {
          this.loginLoading = true
          this.accountKit.idTokenLogin().then((data) => {
            hilog.info(0x0000, 'DEMO_TAG', '%{public}s', 'IdTokenLogin then ' + data);
            this.loginLoading = false
            try {
              this.nickname = data.displayName
              this.avatarUri = data.avatarUri
              this.email = data.email
              this.showText = data.idToken ? ("idToken: " + data.idToken)
                                           : JSON.stringify(data, null, 4)
            } catch (e) {
              hilog.warn(0x0000, 'DEMO_TAG', '%{public}s', 'IdTokenLogin  ' + e);
              this.showText = JSON.stringify(data, null, 4)
            }
          })
        })

        Text(this.showText)
          .fontSize(12)
          .width('75%')
          .margin({ top: 12 })
          .maxLines(20)
          .textOverflow({ overflow: TextOverflow.Ellipsis })

      }.width('100%').height('80%')

    }.height('100%')

  }
}
