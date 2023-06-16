import * as echarts from "echarts/core";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from "echarts/components";
import { PieChart } from "echarts/charts";
import { LabelLayout } from "echarts/features";
import { CanvasRenderer } from "echarts/renderers";
import { useRef, useEffect } from "react";

echarts.use([
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  PieChart,
  CanvasRenderer,
  LabelLayout,
]);
function MyBar({ style, title, data, name }) {
  const domRef = useRef();
  useEffect(() => {
    initChart();
  }, [data]);
  const initChart = () => {
    const myChart = echarts.init(domRef.current);
    myChart.setOption({
      title: {
        text: title,
        subtext: "This Month",
        left: "center",
      },
      tooltip: {
        trigger: "item",
      },
      series: [
        {
          name: name,
          type: "pie",
          radius: "50%",
          data: data,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
        },
      ],
    });
  };
  return <div ref={domRef} style={style}></div>;
}

export default MyBar;
