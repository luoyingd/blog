const TOKEN_KEY = "token";
const USER_ID = "userId";
class Token {
  getToken = () => {
    return localStorage.getItem(TOKEN_KEY);
  };

  saveToken = (token) => {
    localStorage.setItem(TOKEN_KEY, token);
  };

  clearToken = () => {
    localStorage.removeItem(TOKEN_KEY);
  };

  isLogin = () => {
    return this.getToken() !== null;
  };
}

class User {
  getUserId = () => {
    return localStorage.getItem(USER_ID);
  };
  saveUserId = (userId) => {
    localStorage.setItem(USER_ID, userId);
  };
  clearUserId = () => {
    localStorage.removeItem(USER_ID);
  };

  isMe = (userId) => {
    return this.getUserId === userId;
  };
}

const myToken = new Token();
const myUser = new User();
export { myToken, myUser };
