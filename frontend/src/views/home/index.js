import {
  LogoutOutlined,
  HomeOutlined,
  EditOutlined,
  FileSearchOutlined,
} from "@ant-design/icons";
import { Layout, Menu, Popconfirm, Avatar } from "antd";
import React, { useEffect } from "react";
import "./index.scss";
import { Outlet, Link, useLocation, useNavigate } from "react-router-dom";
import userStore from "../../stores/user/userStore";
import { observer } from "mobx-react-lite";
import { baseURL } from "../../utils/http";
import loginStore from "../../stores/login/loginStore";
import uploadStore from "../../stores/common/uploadStore";
import UploadPhoto from "../../components/upload/uploadPhoto";

const { Header, Content, Footer, Sider } = Layout;
const Main = () => {
  const { pathname } = useLocation();
  const navigate = useNavigate();
  useEffect(() => {
    userStore.loadUserProfile();
  }, []);

  const onConfirm = async () => {
    await loginStore.logout();
    navigate("/login", { replace: true });
  };

  return (
    <Layout hasSider className="ant-layout">
      <Sider
        width={180}
        style={{
          overflow: "auto",
          height: "100vh",
          position: "fixed",
          left: 0,
          top: 0,
          bottom: 0,
        }}
      >
        <div className="logo" />
        <Menu
          mode="inline"
          theme="dark"
          defaultSelectedKeys={pathname}
          selectedKeys={pathname}
          style={{ height: "100%", borderRight: 0, marginTop: 20 }}
        >
          <Menu.Item icon={<HomeOutlined />} key="/home">
            <Link to="">Data Overview</Link>
          </Menu.Item>
          <Menu.Item icon={<FileSearchOutlined />} key="/home/allBlog">
            <Link to="allBlog">All Blogs</Link>
          </Menu.Item>
          <Menu.Item icon={<EditOutlined />} key="/home/publish">
            <Link to="publish">Publish a Blog</Link>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout
        className="site-layout"
        style={{
          marginLeft: 140,
        }}
      >
        <Header className="header">
          <div className="user-info">
            {userStore.userInfo.photo ? (
              <Avatar
                size="medium"
                className="user-name"
                src={baseURL + "/common/photo/" + userStore.userInfo.photo}
                onClick={() => {
                  uploadStore.maxCount = 1;
                  uploadStore.callBack = userStore.uploadAvatar;
                  uploadStore.fileType = 2;
                  uploadStore.isShowModal = true;
                }}
              />
            ) : null}
            <span className="user-name">{userStore.userInfo.username}</span>
            <span className="user-logout">
              <Popconfirm
                onConfirm={onConfirm}
                title="Sure to logout?"
                okText="Yes"
                cancelText="Cancel"
              >
                <LogoutOutlined /> Logout
              </Popconfirm>
            </span>
          </div>
        </Header>
        <Content
          style={{
            margin: "24px 16px 0",
            overflow: "initial",
          }}
        >
          <div className="layout-content">
            <Outlet></Outlet>
          </div>
        </Content>
        <Footer
          style={{
            textAlign: "center",
          }}
        >
          Phd Blog ©2021 Created by Luoying D
        </Footer>
      </Layout>
      <UploadPhoto></UploadPhoto>
    </Layout>
  );
};
export default observer(Main);
