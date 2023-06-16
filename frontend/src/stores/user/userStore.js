import { http } from "../../utils/http";
import { makeAutoObservable } from "mobx";
import { myUser } from "../../utils/auth";
import uploadStore from "../common/uploadStore";
import fileStore from "../common/fileStore";

class UserStore {
  userInfo = {
    username: null,
    photo: null,
    id: null,
  };
  allUserIdAndName = [];
  loadUserProfile = () => {
    const id = myUser.getUserId();
    if (id) {
      http.get("/user/getInfo", { params: { userId: id } }).then((res) => {
        this.userInfo = res.data;
      });
    }
  };

  loadAllUser = () => {
    http.get("/user/loadAll").then((res) => {
      this.allUserIdAndName = res.data;
    });
  };

  uploadAvatar = (keys) => {
    http
      .put("/user/update", { id: myUser.getUserId(), photo: keys[0] })
      .then((res) => {
        fileStore.avatarFileList = [];
        uploadStore.isShowModal = false;
        this.loadUserProfile();
      });
  };
  constructor() {
    makeAutoObservable(this);
  }
}

const userStore = new UserStore();
export default userStore;
