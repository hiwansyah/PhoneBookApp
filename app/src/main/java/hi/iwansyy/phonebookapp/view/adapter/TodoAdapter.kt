package hi.iwansyy.phonebookapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hi.iwansyy.phonebookapp.R
import hi.iwansyy.phonebookapp.databinding.ItemTodoBinding
import hi.iwansyy.phonebookapp.model.TodoModel
class TodoAdapter(private val context: Context, private val listener: TodoListener) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var list = mutableListOf<TodoModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun insertTodo(todoModel: TodoModel) {
        list.add(0, todoModel)
        notifyItemInserted(0)
    }

    fun updateTodo(todoModel: TodoModel) {
        val index = list.indexOfFirst { it.id == todoModel.id }
        if (index != -1) {
            list[index] = todoModel
            notifyItemChanged(index)
        }
    }

    fun deleteTodo(todoModel: TodoModel) {
        val index = list.indexOfFirst { it.id == todoModel.id }
        if (index != -1) {
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }


    interface TodoListener {
        fun onChange(todoModel: TodoModel)
        fun onDelete(todoModel: TodoModel)
        fun onFavorite(todoModel: TodoModel)
    }

    inner class ViewHolder(val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(todoModel: TodoModel){
            binding.run {
                tvName.text = todoModel.task
                btnFavorite.setImageResource(if (todoModel.status) R.drawable.ic_favorite else R.drawable.ic_unfavorite)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        return ViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        val model by lazy { list[position] }
        holder.bindData(model)
        holder.binding.run {
            cvTodos.setOnClickListener{ listener.onFavorite(model) }
            btnFavorite.setOnClickListener{ listener.onChange(model) }
            ivTodo.setOnClickListener{ listener.onDelete(model) }
        }
    }

    override fun getItemCount(): Int = list.size
}