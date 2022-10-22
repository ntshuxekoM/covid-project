import { Component, OnInit, AfterViewInit } from "@angular/core";
import Chart from "chart.js";
import { ToastrService } from "ngx-toastr";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { DashboardService } from "src/app/service/dashboard.service";

@Component({
  selector: "app-card-line-chart",
  templateUrl: "./card-line-chart.component.html",
})
export class CardLineChartComponent implements OnInit {
  public futurePredictionData: any;

  constructor(private service: DashboardService, private authSevice: AuthServiceService, private toastr: ToastrService) { }


  ngOnInit() {
  }

  ngAfterViewInit() {
    this.service.getFuturePrediction(this.authSevice.getLoggedUser()).subscribe({
      next: (results) => {

        this.futurePredictionData = results;
        console.log("Future Prediction Data: " + JSON.stringify(this.futurePredictionData));

        var config = {
          type: "line",
          data: {
            labels: [],
            datasets: [],
          },
          options: {
            maintainAspectRatio: false,
            responsive: true,
            title: {
              display: false,
              text: "Sales Charts",
              fontColor: "white",
            },
            legend: {
              labels: {
                fontColor: "white",
              },
              align: "end",
              position: "bottom",
            },
            tooltips: {
              mode: "index",
              intersect: false,
            },
            hover: {
              mode: "nearest",
              intersect: true,
            },
            scales: {
              xAxes: [
                {
                  ticks: {
                    fontColor: "rgba(255,255,255,.7)",
                  },
                  display: true,
                  scaleLabel: {
                    display: false,
                    labelString: "Month",
                    fontColor: "white",
                  },
                  gridLines: {
                    display: false,
                    borderDash: [2],
                    borderDashOffset: [2],
                    color: "rgba(33, 37, 41, 0.3)",
                    zeroLineColor: "rgba(0, 0, 0, 0)",
                    zeroLineBorderDash: [2],
                    zeroLineBorderDashOffset: [2],
                  },
                },
              ],
              yAxes: [
                {
                  ticks: {
                    fontColor: "rgba(255,255,255,.7)",
                  },
                  display: true,
                  scaleLabel: {
                    display: false,
                    labelString: "Value",
                    fontColor: "white",
                  },
                  gridLines: {
                    borderDash: [3],
                    borderDashOffset: [3],
                    drawBorder: false,
                    color: "rgba(255, 255, 255, 0.15)",
                    zeroLineColor: "rgba(33, 37, 41, 0)",
                    zeroLineBorderDash: [2],
                    zeroLineBorderDashOffset: [2],
                  },
                },
              ],
            },
          },
        };


        //Setting labels using data from the service
        this.futurePredictionData.labelList.forEach((label) => {
          config.data.labels.push(label);
        });

        //Creating dataset using data from the service
        this.futurePredictionData.datasetList.forEach((data) => {
          var dataset =
          {
            label: data.label,
            backgroundColor: data.backgroundColor,
            borderColor: data.borderColor,
            data: data.dataList,
            fill: data.fill,
          };

          config.data.datasets.push(dataset);
        });

        let ctx: any = document.getElementById("line-chart") as HTMLCanvasElement;
        ctx = ctx.getContext("2d");
        new Chart(ctx, config);

      },
      error: (error) => {
        console.log(JSON.stringify(error));
        this.toastr.error('Servie unavailable');
      }
    })



  }



}
