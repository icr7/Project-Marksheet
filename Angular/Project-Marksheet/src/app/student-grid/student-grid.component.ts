import { Component,Input, OnInit, Output, EventEmitter} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { Student } from '../student';
import { MarksheetService } from '../marksheet.service';


@Component({
  selector: 'app-student-grid',
  templateUrl: './student-grid.component.html',
  styleUrls: ['./student-grid.component.css']
})

export class StudentGridComponent implements OnInit {
   gridApi;
   gridColumnApi;
   columnDefs;
   rowSelection;
   obj:Student=new Student();
@Input() public parentData

clickEventSub:Subscription;

  rowData: Observable<any[]>;

  constructor(private http: HttpClient, private service:MarksheetService) {
    this.columnDefs = [
      { field: 'id', width: 40 },
      { field: 'name', width: 120 },
      { field: 'english', width: 80 },
      { field: 'hindi', width: 80 },
      { field: 'maths', width: 80 },
      { field: 'science', width: 80 },
      { field: 'sst', width: 80 }
    ];
    this.rowSelection = 'single';
    this.clickEventSub=this.service.getClickEvent().subscribe(()=>{this.refresh();})
  }

  ngOnInit(): void {

  }

  @Output() feed:EventEmitter<any>=new EventEmitter()
  onSelectionChanged(event: any) {
    this.obj=this.gridApi.getSelectedRows()
    console.log("selection: ",this.obj[0].name); 
    this.feed.emit(this.obj);

  }

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;

    this.rowData = this.http.get<any[]>('http://localhost:8080/api/allStudent')
  }

  refresh(){
    this.gridApi.refreshCells();
  }
}
