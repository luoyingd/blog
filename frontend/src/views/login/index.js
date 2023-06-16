import { Card } from "antd";
import logo from "../../assets/logo.png";
import "./index.scss";
import { Outlet } from "react-router-dom";
function Login() {
  return (
    <div className="login">
      <Card className="login-container">
        <img src={logo} className="login-logo" alt="/"></img>
        <span className="login-title">Phd Blog</span>
        <Outlet></Outlet>
      </Card>
    </div>
  );
}

export default Login;
