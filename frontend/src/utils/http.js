import axios from "axios";
import { message } from "antd";
import { myToken } from "./auth";
import loadingStore from "../stores/common/loadingStore";
import history from "./history";
const baseURL = "http://localhost:8090/api";
const http = axios.create({
  baseURL: baseURL,
  timeout: 20000,
  withCredentials: true,
});
const http_file = axios.create({
  baseURL: baseURL,
  timeout: 20000,
  withCredentials: true,
});

const beforeRequest = (config) => {
  const token = myToken.getToken();
  if (token) {
    config.headers["token"] = token;
  }
  loadingStore.isLoading = true;
  return config;
};
const beforeRequestFile = (config) => {
  const token = myToken.getToken();
  if (token) {
    config.headers["token"] = token;
  }
  config.headers["Content-Type"] = "multipart/form-data";
  loadingStore.isLoading = true;
  return config;
};

http.interceptors.request.use(beforeRequest);
http_file.interceptors.request.use(beforeRequestFile);

const responseSuccess = (response) => {
  const data = response.data;
  loadingStore.isLoading = false;
  if (data !== null) {
    if (data.code !== 200) {
      message.error(data.msg, [3]);
      if (data.code === 401) {
        history.push("/login");
      }
      return Promise.reject();
    }
  }
  return Promise.resolve(data);
};
const responseFailed = (error) => {
  const { response } = error;
  loadingStore.isLoading = false;
  if (response) {
    return Promise.reject();
  } else if (!window.navigator.onLine) {
    return Promise.reject(new Error("Please check the Internet..."));
  }
  return Promise.reject(error);
};
http.interceptors.response.use(responseSuccess, responseFailed);
http_file.interceptors.response.use(responseSuccess, responseFailed);

export { http, baseURL, http_file };
