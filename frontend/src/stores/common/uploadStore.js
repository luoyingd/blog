import { http } from "../../utils/http";
import { makeAutoObservable } from "mobx";
import { myUser } from "../../utils/auth";

class UploadStore {
  isShowModal = false;
  maxCount = 0;
  callBack = null;
  fileType = 0; // 0 for quill, 1 for cover, 2 for avatar
  constructor() {
    makeAutoObservable(this);
  }
}

const uploadStore = new UploadStore();
export default uploadStore;
