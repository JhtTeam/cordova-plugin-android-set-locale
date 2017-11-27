/*global cordova, module*/

module.exports = {
  setLocale: function (name, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "CordovaAndroidSetLocale", "setLocale", [name]);
  }
};
