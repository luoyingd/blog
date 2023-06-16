import { http } from "../../utils/http";
import { makeAutoObservable } from "mobx";
import { myToken, myUser } from "../../utils/auth";

class LoginStore {
  // await只在async修饰的函数中有效
  login = async (username, password) => {
    const loginResult = await http.post("/user/login", {
      username: username,
      password: password,
    });
    const token = loginResult.data.token;
    const userId = loginResult.data.userId;
    // token持久化
    myToken.saveToken(token);
    myUser.saveUserId(userId);
  };

  register = async (data) => {
    const registerResult = await http.post("/user/register", {
      username: data.username,
      password: data.password,
      email: data.email,
    });
    return registerResult;
  };

  resetPwdSendMail = async (email) => {
    const result = await http.post("/user/resetPwdSendMail", {
      email: email,
    });
    return result;
  };

  resetPwd = async (password, token, email) => {
    const result = await http.post("/user/resetPwd", {
      password: password,
      token: token,
      email: email,
    });
    return result;
  };

  logout = async () => {
    const result = await http.post("/user/logout", {
      userId: myUser.getUserId(),
    });
    myToken.clearToken();
    myUser.clearUserId();
    return result;
  };

  constructor() {
    makeAutoObservable(this);
  }
}

const loginStore = new LoginStore();
export default loginStore;
