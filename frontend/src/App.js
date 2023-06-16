import Login from "./views/login";
import Main from "./views/home/index";
import Register from "./views/login/register";
import MyForm from "./views/login/form";
import { Route, Routes } from "react-router-dom";
import "antd/dist/reset.css";
import "./App.scss";
import AuthLogin from "./components/authLogin";
import ResetPwdSendMail from "./views/login/resetPwdSendMail";
import ResetPwd from "./views/login/resetPwd";
import Data from "./views/data";
import Blog from "./views/blog";
import Publish from "./views/publish";
import { unstable_HistoryRouter as HistoryRouter } from "react-router-dom";
import history from "./utils/history";
import BlogInfo from "./views/blog/blogInfo";
import Error from "./views/404";

function App() {
  return (
    // use history if need route in non-component js
    <HistoryRouter history={history}>
      <div className="App">
        <Routes>
          <Route exact path="/" element={<Main></Main>} />
          <Route path="/login" element={<Login></Login>}>
            <Route index element={<MyForm></MyForm>}></Route>
            <Route path="register" element={<Register></Register>}></Route>
            <Route
              path="resetPwdSendMail"
              element={<ResetPwdSendMail></ResetPwdSendMail>}
            ></Route>
            <Route path="resetPwd" element={<ResetPwd></ResetPwd>}></Route>
          </Route>
          {/* use high-level component for auth */}
          <Route
            path="/home"
            element={
              <AuthLogin>
                <Main></Main>
              </AuthLogin>
            }
          >
            <Route
              index
              element={
                <AuthLogin>
                  <Data></Data>
                </AuthLogin>
              }
            ></Route>
            <Route path="allBlog">
              <Route
                index
                element={
                  <AuthLogin>
                    <Blog></Blog>
                  </AuthLogin>
                }
              ></Route>
              <Route
                path="info/:id"
                element={
                  <AuthLogin>
                    <BlogInfo></BlogInfo>
                  </AuthLogin>
                }
              ></Route>
            </Route>
            <Route
              path="publish"
              element={
                <AuthLogin>
                  <Publish></Publish>
                </AuthLogin>
              }
            ></Route>
          </Route>
          {/* 404配置 */}
          <Route path="*" element={<Error></Error>}></Route>
        </Routes>
      </div>
    </HistoryRouter>
  );
}

export default App;
