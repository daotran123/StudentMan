package vn.edu.hust.studentman

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageRemove.setOnClickListener{
      AlertDialog.Builder(holder.itemView.context)
        .setTitle("Bạn có muốn xóa không?")
        .setNegativeButton("No"){ dialog, which ->

        }
        .setPositiveButton("Yes"){dialog, which ->
          val Name = student.studentName
          val MSSV = student.studentId
          students.removeAt(position)
          notifyItemRemoved(position)
          notifyItemRangeChanged(position, students.size)

          Snackbar.make(it, "Người dùng đã bị xóa", Snackbar.LENGTH_LONG)
            .setAction("UNDO"){
              students.add(StudentModel(Name, MSSV))
              notifyItemRangeChanged(position, students.size)
            }
            .show()
        }
        .show()
    }

    holder.imageEdit.setOnClickListener {
      val dialog = Dialog(holder.itemView.context)
      dialog.setContentView(R.layout.edit_layout)

      val editName = dialog.findViewById<EditText>(R.id.edit_name)
      val editMssv = dialog.findViewById<EditText>(R.id.edit_mssv)

      editName.setText(student.studentName)
      editMssv.setText(student.studentId)

      dialog.findViewById<Button>(R.id.button_edit).setOnClickListener {
        val Name = editName.text.toString()
        val MSSV = editMssv.text.toString()

        // Cập nhật thông tin student
        student.studentName = Name
        student.studentId = MSSV

        notifyItemChanged(position)  // Thông báo rằng item đã thay đổi
        dialog.dismiss()


      }
      dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
      dialog.show()
    }
  }
}