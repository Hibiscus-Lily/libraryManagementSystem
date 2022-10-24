function passwordEncryption() {
    const publicKey = '-----BEGIN PUBLIC KEY----- MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCODnqqHY+9aBdERXjNe+qlMjuMApfJyWUqwiv4diobtgQPSao//BrfVXPTcoLJtODLZ4EdG2+Hw2/I1a3MHg5Gl26ejXwVfmuaIfEsPDMW++tNSXZRoTHFWFNSTdNLLYFqbomaVRhZDhqDEbHrheD6Ag33cxzCCFt50Reb99YilQIDAQAB -----END PUBLIC KEY-----';
    const encrypt = new JSEncrypt();
    const oneMD5 = (hex_md5("111"))
    const MD5password = hex_md5(oneMD5)
    encrypt.setPublicKey(publicKey);
    console.log(MD5password)
    const encryptData = encrypt.encrypt(MD5password);


    console.log(encryptData)
}

passwordEncryption()