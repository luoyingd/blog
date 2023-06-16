import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Form, Input, Spin } from "antd";
import "./index.scss";
import { observer } from "mobx-react-lite";
import loginStore from "../../stores/login/loginStore";
import { useNavigate } from "react-router-dom";
import loadingStore from "../../stores/common/loadingStore";
function MyForm() {
  const navigate = useNavigate();
  const onFinish = async (values) => {
    await loginStore.login(values.username, values.password);
    navigate("/home", { replace: true });
  };
  return (
    <div className="login-container-login">
      <Form name="normal_login" className="login-form" onFinish={onFinish}>
        <Form.Item
          name="username"
          rules={[
            {
              required: true,
              message: "Please input your Username or Email!",
            },
          ]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Username or Email"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: "Please input your Password!",
            },
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>
        <Form.Item>
          <a className="login-form-forgot" href="/login/resetPwdSendMail">
            Forgot or change password
          </a>
        </Form.Item>

        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
            disabled={loadingStore.isLoading}
          >
            Log in
          </Button>
          <Spin spinning={loadingStore.isLoading}></Spin>
          <a href="/login/register" className="register-link">
            register now!
          </a>
        </Form.Item>
      </Form>
    </div>
  );
}

export default observer(MyForm);
