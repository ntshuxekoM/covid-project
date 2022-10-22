import { Component, OnInit, AfterViewInit } from "@angular/core";
import Chart from "chart.js";
import { ToastrService } from "ngx-toastr";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { DashboardService } from "src/app/service/dashboard.service";

@Component({
  selector: "app-card-bar-chart",
  templateUrl: "./card-bar-chart.component.html",
})
export class CardBarChartComponent implements OnInit, AfterViewInit {
  public vaccinationData: any;
  constructor(private service: DashboardService, private authSevice: AuthServiceService, private toastr: ToastrService) { }

  

  ngOnInit() { }
  ngAfterViewInit() {

    this.service.getVaccinationData(this.authSevice.getLoggedUser()).subscribe({
      next: (results) => {

        this.vaccinationData = results;

        let config = {
          type: "bar",
          data: {
            labels: [],
            datasets: [],
          },
          options: {
            maintainAspectRatio: false,
            responsive: true,
            title: {
              display: false,
              text: "Orders Chart",
            },
            tooltips: {
              mode: "index",
              intersect: false,
            },
            hover: {
              mode: "nearest",
              intersect: true,
            },
            legend: {
              labels: {
                fontColor: "rgba(0,0,0,.4)",
              },
              align: "end",
              position: "bottom",
            },
            scales: {
              xAxes: [
                {
                  display: false,
                  scaleLabel: {
                    display: true,
                    labelString: "Month",
                  },
                  gridLines: {
                    borderDash: [2],
                    borderDashOffset: [2],
                    color: "rgba(33, 37, 41, 0.3)",
                    zeroLineColor: "rgba(33, 37, 41, 0.3)",
                    zeroLineBorderDash: [2],
                    zeroLineBorderDashOffset: [2],
                  },
                },
              ],
              yAxes: [
                {
                  display: true,
                  scaleLabel: {
                    display: false,
                    labelString: "Value",
                  },
                  gridLines: {
                    borderDash: [2],
                    drawBorder: false,
                    borderDashOffset: [2],
                    color: "rgba(33, 37, 41, 0.2)",
                    zeroLineColor: "rgba(33, 37, 41, 0.15)",
                    zeroLineBorderDash: [2],
                    zeroLineBorderDashOffset: [2],
                  },
                },
              ],
            },
          },
        };


        //Setting labels using data from the service
        this.vaccinationData.labelList.forEach((label) => {
          config.data.labels.push(label);
        });

        //Creating dataset using data from the service
        this.vaccinationData.datasetList.forEach((data) => {
          var dataset =
          {
            label: data.label,
            backgroundColor: data.backgroundColor,
            borderColor: data.borderColor,
            data: data.dataList,
            fill: data.fill,
            barThickness:15,
          };

          config.data.datasets.push(dataset);
        });


        let ctx: any = document.getElementById("bar-chart");
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
