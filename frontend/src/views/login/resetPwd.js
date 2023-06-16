import { Button, Form, Input, message } from "antd";
import "./index.scss";
import { observer } from "mobx-react-lite";
import loginStore from "../../stores/login/loginStore";
import { useNavigate } from "react-router-dom";
import { useSearchParams } from "react-router-dom";
import Error from "../404";
function ResetPwd() {
  const navigate = useNavigate();
  const [params] = useSearchParams();
  const token = params.get("token");
  const email = params.get("email");
  const onFinish = async (values) => {
    await loginStore.resetPwd(values.password, token, email);
    message.success("Successfully reset!", [3]);
    navigate("/login", { replace: true });
  };
  return (
    <div className="login-container-reset">
      <Form name="normal_login" className="login-form" onFinish={onFinish}>
        <Form.Item
          name="password"
          label="Password"
          rules={[
            {
              required: true,
              message: "Please input your new password!",
            },
          ]}
          hasFeedback
        >
          <Input.Password />
        </Form.Item>
        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
          >
            Submit
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}

export default observer(ResetPwd);
