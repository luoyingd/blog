import { Button, Form, Input, Modal, Spin } from "antd";
import "./index.scss";
import { observer } from "mobx-react-lite";
import loginStore from "../../stores/login/loginStore";
import { useNavigate } from "react-router-dom";
import loadingStore from "../../stores/common/loadingStore";
import modalStore from "../../stores/common/modalStore";
function ResetPwdSendMail() {
  const navigate = useNavigate();
  const handleOk = () => {
    modalStore.isModalOpen = false;
    navigate("/login", { replace: true });
  };
  const onFinish = async (values) => {
    await loginStore.resetPwdSendMail(values.email);
    modalStore.isModalOpen = true;
  };
  return (
    <div className="login-container-reset">
      <Modal
        title="Success"
        open={modalStore.isModalOpen}
        onOk={handleOk}
        onCancel={() => {
          modalStore.alterModal(false);
        }}
      >
        <p>
          We have sent a link to your email, please check and reset within 10
          minutes.
        </p>
      </Modal>
      <Form name="normal_login" className="login-form" onFinish={onFinish}>
        <Form.Item
          name="email"
          label="E-mail"
          rules={[
            {
              required: true,
              message: "Please input your email!",
            },
          ]}
        >
          <Input placeholder="Send an e-mail to reset password" />
        </Form.Item>
        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
            disabled={loadingStore.isLoading}
          >
            Send
          </Button>
          <Spin spinning={loadingStore.isLoading}></Spin>
          <a href="/login" className="register-link">
            Log in
          </a>
        </Form.Item>
      </Form>
    </div>
  );
}

export default observer(ResetPwdSendMail);
