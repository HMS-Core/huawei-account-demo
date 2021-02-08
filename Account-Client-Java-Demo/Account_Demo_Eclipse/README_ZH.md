# ��Ϊ�ʺŷ���ͻ���Eclipseʾ������

���� | [English](https://github.com/HMS-Core/huawei-account-demo/blob/master/Account-Client-Java-Demo/Account_Demo_Eclipse/README.md) 

## Ŀ¼
* [���](#���)
* [��װ](#��װ)
* [����Ҫ��](#����Ҫ��)
* [����](#����)
* [��������](#��������)
* [ʾ������](#ʾ������)
* [��Ȩ���](#��Ȩ���)

## ���
Androidʾ������Ի�Ϊ�ʺŷ���HUAWEI Account Kit���Ŀͻ��˽ӿڽ��з�װ�������ḻ��ʾ�����򣬷������ο���ֱ��ʹ�á��������£�
hmssample��ʾ�����������ʵ�ֵ�¼����Ȩ��¼���˳��ʺŹ��ܡ�
logger����ʵ����־��¼��

## ��װ
����ʹ�ñ�ʾ�������еĸ��ֹ��ܣ���ȷ�������豸���Ѱ�װ��Ϊ�ƶ�����HMS Core��4.0��

## ����Ҫ��
�Ƽ�ʹ��Android SDK 23�����ϰ汾��JDK 1.8�����ϰ汾��

## ����
Contant.java�������²�����
CLIENT_ID����app ID���ɴ�AppGallery Connect�ϻ�ȡ��
CERT_URL���ɴ�jwks_uri�ֶλ�ȡ��ԿURI�����ʹ�ԿURI��ȡ��Կ��
ID_TOKEN_ISSUE����ID Token��iss�ֶε�ֵ��ͬ��

## ��������
1. �����Ŀ������������ο���Eclipse��Ŀ�м���HMS Core SDK��

2. ��Eclipse IDE���һ�����Ŀ���̣��������Export����
![image.png](http://image.huawei.com/tiny-lts/v1/images/d9a59bb5b945f8c95979be3b7b3b1d37_511x542.png@900-0-90-f.png)

3. �����Next�����롰Keystore selection��ҳ�档

4. ѡ��Use existing keystore���������Browse����ѡ��Ŀ¼�µġ�lightregion.jks���ļ������������롰android����
![image.png](http://image.huawei.com/tiny-lts/v1/images/f614cfefc03ee9dbbc1dba2daeedc304_510x542.png@900-0-90-f.png)

5. ѡ��Use existing key������Alias������Ϊ��androiddebugkey�������������롰android����
![image.png](http://image.huawei.com/tiny-lts/v1/images/d70ab5ecddbd53eb5ba63c239f7efa1f_510x542.png@900-0-90-f.png)

6. �����Browse����ѡ��APK����λ�ò������Finish����
![image.png](http://image.huawei.com/tiny-lts/v1/images/db9fd9f9b2ebd23f3f99685bacc30192_511x540.png@900-0-90-f.png)

7. ��װ������ʾ��Ӧ�ã����Կ�������Ч����
![image.png](http://image.huawei.com/tiny-lts/v1/images/b3e3cf07c5da4ddd419559ba7e76fd30_276x599.png@900-0-90-f.png)

## ʾ������
��ʾ�������ṩ������ʹ�ó�����
1. ID Tokenģʽ��¼����ģʽ��������¼+ID Token��֤����
2. Authorization Codeģʽ��¼��
3. �˳��ʺš�

����ҵ���߼�����HuaweiIdActivity.java��ʵ��ID Tokenģʽ��¼��Authorization Code��¼����IDTokenParse.java��ʵ��ID Token��֤��

## ��Ȩ���
��Ϊ�ʺŷ���Androidʾ�����뾭��[Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)��Ȩ��ɡ�