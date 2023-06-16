import { myToken } from "../utils/auth";
import { Navigate } from "react-router-dom";
import { message } from "antd";
function AuthLogin({ children }) {
  const token = myToken.getToken();
  if (token) {
    return <>{children}</>;
  }
  message.error("Need login!", [3]);
  return <Navigate to="/login" replace={true}></Navigate>;
}

export default AuthLogin;
