import MyBar from "../../components/charts/bar";
import { Col, Row, Statistic} from "antd";
import { CommentOutlined, BookOutlined, UserOutlined } from "@ant-design/icons";
import { observer } from "mobx-react-lite";
import myDataStore from "../../stores/data/dataStore";
import React, { useEffect} from "react";
import * as echarts from "echarts/core";
import { BarChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
} from "echarts/components";
import { LabelLayout, UniversalTransition } from "echarts/features";
import { CanvasRenderer } from "echarts/renderers";

echarts.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  BarChart,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer,
]);

function Data() {
  const chartStyle = { width: "500px", height: "400px", marginTop: "40px" };
  useEffect(() => {
    myDataStore.loadTotal();
    myDataStore.loadMy();
    myDataStore.loadTop5Topic();
    myDataStore.loadTop5Author();
  }, []);

  return (
    <div>
      <Row gutter={12}>
        <Col span={3}>
          <Statistic
            title="Total Users"
            value={myDataStore.totalUser}
            prefix={<UserOutlined />}
          />
        </Col>
        <Col span={3}>
          <Statistic
            title="Total Blogs"
            value={myDataStore.totalBlog}
            prefix={<BookOutlined />}
          />
        </Col>
        <Col span={3}>
          <Statistic
            title="My Blogs"
            value={myDataStore.myBlog}
            prefix={<BookOutlined />}
          />
        </Col>
        <Col span={3}>
          <Statistic
            title="My Feedback"
            value={myDataStore.myComments}
            prefix={<CommentOutlined />}
          />
        </Col>
      </Row>
      <Row>
        <Col span={8}>
          <MyBar
            style={chartStyle}
            title="TOP 5 Popular Topics"
            data={myDataStore.top5Topic}
            name="Blogs"
          ></MyBar>
        </Col>
        <Col span={8} offset={4}>
          <MyBar
            style={chartStyle}
            title="TOP 5 Popular Authors"
            data={myDataStore.top5Author}
            name="Feedbacks"
          ></MyBar>
        </Col>
      </Row>
    </div>
  );
}

export default observer(Data);
