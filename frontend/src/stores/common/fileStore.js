import { makeAutoObservable } from "mobx";
import { http_file } from "../../utils/http";

class FileStore {
  quillFileList = [];
  coverFileList = [];
  avatarFileList = [];
  loading = false;
  constructor() {
    makeAutoObservable(this);
  }
  checkFile = (fileList) => {
    return fileList.every((item) => {
      return (
        (item.type === "image/jpeg" ||
          item.type === "image/png" ||
          item.type === "image/jpg") &&
        item.size / 1024 / 1024 < 20
      );
    });
  };

  uploadFiles = (callBack, fileList) => {
    let keyList = [];
    var taskStack = [];
    this.loading = true;
    fileList.forEach((file) => {
      taskStack.push(
        new Promise((resolve, reject) => {
          http_file
            .post("/common/uploadPhoto", {
              multipartFile: file.originFileObj,
            })
            .then((res) => {
              resolve(res.data);
            });
        })
      );
    });
    Promise.all(taskStack).then((values) => {
      values.forEach((value) => {
        keyList.push(value);
      });
      this.coverFileList = [];
      this.quillFileList = [];
      this.avatarFileList = [];
      this.loading = false;
      callBack(keyList);
    });
  };
}

const fileStore = new FileStore();
export default fileStore;
